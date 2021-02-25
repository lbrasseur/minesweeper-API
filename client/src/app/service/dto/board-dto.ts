import { CellDto } from './cell-dto';

export class BoardDto {
  id: string;
  owner: string;
  cells: CellDto[][];
}
