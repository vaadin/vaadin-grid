
import {GridFilter} from '../@types/interfaces';


declare function FilterMixin<T extends new (...args: any[]) => {}>(base: T): T & FilterMixinConstructor;

interface FilterMixinConstructor {
  new(...args: any[]): FilterMixin;
}

interface FilterMixin {
  ready(): void;
  _mapFilters(): GridFilter[];
}


export {FilterMixin, FilterMixinConstructor};
