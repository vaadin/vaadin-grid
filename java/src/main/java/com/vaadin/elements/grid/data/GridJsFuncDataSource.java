package com.vaadin.elements.grid.data;

import java.util.List;

import com.google.gwt.query.client.js.JsUtils;
import com.vaadin.client.data.CacheStrategy;
import com.vaadin.elements.common.js.JS;
import com.vaadin.elements.common.js.JSArray;
import com.vaadin.elements.common.js.JSFunction2;
import com.vaadin.elements.grid.GridElement;
import com.vaadin.elements.grid.config.JSDataRequest;
import com.vaadin.shared.ui.grid.Range;

/**
 * Datasource where requestRows() is delegated to a js native function
 */
public class GridJsFuncDataSource extends GridDataSource {

    private JSFunction2<JSDataRequest, JSFunction2<JSArray<?>, Double>> jsFunction;
    private boolean initialRowSetReceived;

    public GridJsFuncDataSource(
            JSFunction2<JSDataRequest, JSFunction2<JSArray<?>, Double>> jsFunction,
            GridElement grid) {
        super(grid);
        this.jsFunction = jsFunction;

        // Force viewport recomputing
        this.ensureAvailability(0, 0);

        // This strategy saves a lot of server requests since we keep more elements in cache.
        // Basically we return a range with the new items to query, and all items already in
        // the cache, so as the DataSource does not remove them.
        setCacheStrategy(new CacheStrategy.DefaultCacheStrategy() {
            @Override
            public Range getMaxCacheRange(Range displayedRange, Range cachedRange, Range estimatedAvailableRange) {
                int cacheSize = getMaximumCacheSize(displayedRange.length());
                // We don't want to loose items that are already in memory.
                // We enlarge the returned Range to fit all the cached size.
                // If the viewport is out of the cache edges we have to discard
                // data because cache only supports correlative items.
                int left = displayedRange.getStart() > cachedRange.getEnd() ? cacheSize
                        : Math.max(cacheSize, displayedRange.getStart() - cachedRange.getStart());
                int right = displayedRange.getEnd() < cachedRange.getStart() ? cacheSize
                        : Math.max(cacheSize, cachedRange.getEnd() - displayedRange.getEnd());
                Range r = displayedRange.expand(left, right).restrictTo(estimatedAvailableRange);
                return r;
            }
        });

        // Grid size might be 0, so we'll check it here and make an initial
        // data request so as the server can tell us the size in the callback
        grid.then(o -> {
            if (size() == 0) {
                refreshItems();
            }
            return null;
        });
    }

    @Override
    public void ensureAvailability(int firstRowIndex, int numberOfRows) {
        // Requests are based on the viewport size computed in Escalator from CSS.
        // But it happens that the very first time, Escalator sends an event
        // which is coming later, and bringing an incorrect value of 0 or 1;
        if (numberOfRows < 2) {
            numberOfRows = (int)gridElement.getGrid().getEscalator().getHeightByRows();
        }
        super.ensureAvailability(firstRowIndex, numberOfRows);
    }

    public void setJSFunction(
            JSFunction2<JSDataRequest, JSFunction2<JSArray<?>, Double>> jsFunction) {
        if (this.jsFunction != jsFunction) {
            this.jsFunction = jsFunction;
            refreshItems();
            gridElement.getSelectionModel().reset();
        }
    }

    @Override
    protected void requestRows(final int firstRowIndex, final int numberOfRows,
            final RequestRowsCallback<Object> callback) {

        JSDataRequest jsDataRequest = JS.createJsObject();
        jsDataRequest.setIndex(firstRowIndex);
        jsDataRequest.setCount(numberOfRows);
        jsDataRequest.setSortOrder(JsUtils.prop(gridElement.getContainer(),
                "sortOrder"));

        gridElement.setLoadingDataClass(true);

        jsFunction.f(jsDataRequest, (array, totalSize) -> {
            List<Object> list = JS.asList(array);
            for (int i = 0; i < list.size(); i++) {
                if (JS.isPrimitiveType(list.get(i))) {
                    list.set(i, new DataItemContainer(list.get(i)));
                }
            }

            if (totalSize != null) {
                setSize(totalSize.intValue());
            }
            if (callback != null) {
                callback.onResponse(list, size());
            }

            gridElement.setLoadingDataClass(false);

            if (!initialRowSetReceived && !list.isEmpty()) {
                initialRowSetReceived = true;
                gridElement.updateWidth();
            }
        });
    }
}
