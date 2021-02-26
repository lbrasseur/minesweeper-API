import { CellState } from './cell-state';

export class CellDto {
  state: CellState;
  hasMine: boolean;
  borderingMines: number;
}
