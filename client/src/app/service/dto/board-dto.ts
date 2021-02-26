import { CellDto } from './cell-dto';
import { BoardState } from './board-state';

export class BoardDto {
  id: string;
  owner: string;
  cells: CellDto[][];
  state: BoardState;
}
