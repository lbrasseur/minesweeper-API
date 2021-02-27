import { BoardIdDto } from './board-id-dto';

export class CellIdDto extends BoardIdDto {
  column: number;
  row: number;
}
