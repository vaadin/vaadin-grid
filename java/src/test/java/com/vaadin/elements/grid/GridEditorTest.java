package com.vaadin.elements.grid;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import com.google.gwtmockito.GwtMockitoTestRunner;
import com.vaadin.elements.grid.GridEditor;
import com.vaadin.elements.grid.GridElement;
import com.vaadin.elements.grid.ViolatedGrid;
import com.vaadin.elements.grid.config.JSEditorHandler;

@RunWith(GwtMockitoTestRunner.class)
public class GridEditorTest {

    @Mock
    private ViolatedGrid grid;

    @Mock
    private JSEditorHandler handler;
    private GridEditor editor;

    @Before
    public void setup() {
        GridElement gridComponent = mock(GridElement.class);

        when(gridComponent.getGrid()).thenReturn(grid);

        editor = new GridEditor(gridComponent, handler);
    }

    @Test
    public void activeEditorIsCancelled() {
        when(grid.isEditorActive()).thenReturn(true);

        editor.cancel();

        verify(grid).cancelEditor();
    }

    @Test
    public void inActiveEditorIsNotCancelled() {
        when(grid.isEditorActive()).thenReturn(false);

        editor.cancel();

        verify(grid, times(0)).cancelEditor();
    }

    @Test
    public void editorIsCancelledOnReselectWhenActive() {
        when(grid.isEditorActive()).thenReturn(true);

        editor.editRow(0);

        verify(grid).cancelEditor();
    }
}
