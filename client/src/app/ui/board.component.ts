import { Component, OnInit } from '@angular/core';
import { BoardManager } from '../business/board-manager';
import { BoardDto } from '../service/dto/board-dto';
import { BoardState } from '../service/dto/board-state';
import { CellDto } from '../service/dto/cell-dto';
import { CellState } from '../service/dto/cell-state';

import { Observable } from 'rxjs';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {
  boardId: string;
  boardState: BoardState = BoardState.PLAYING;
  cells: CellDto[][];

  constructor(private manager: BoardManager) { }

  ngOnInit(): void {
  }

  createBoard(width: number,
      height: number,
      mines: number) {
    this.updateBoard(this.manager.createBoard(width, height, mines));
  }

  clickCell(cell: CellDto,
      column: number,
      row: number) {
    if (cell.state == CellState.INITIAL) {
      this.updateBoard(this.manager.click(this.boardId, column, row));
    }
  }

  rightClick(cell: CellDto,
      column: number,
      row: number) {
    switch (cell.state) {
      case CellState.INITIAL:
        this.updateBoard(this.manager.redFlag(this.boardId, column, row));
        break;
      case CellState.RED_FLAG:
        this.updateBoard(this.manager.questionMark(this.boardId, column, row));
        break;
      case CellState.QUESTION_MARK:
        this.updateBoard(this.manager.initial(this.boardId, column, row));
        break;
    }
  }

  cellAsset(cell: CellDto): string {
    return cell.state == CellState.CLICKED && cell.hasMine
      ? "mine"
      : cell.state.toLowerCase();
  }

  cellColor(cell: CellDto): string {
    switch (cell.borderingMines) {
      case 1: return "blue";
      case 2: return "green";
      case 3: return "red";
      case 4: return "darkBlue";
      case 5: return "brown";
      default: return "black";
    }
  }

  cellNumber(cell: CellDto): string {
    return cell.state == CellState.CLICKED &&  cell.borderingMines > 0 && !cell.hasMine
      ? (""+cell.borderingMines)
      : "";
  }

  private updateBoard(operation: Observable<BoardDto>) {
    operation.subscribe((dto: BoardDto) => {
      this.boardId = dto.id;
      this.boardState = dto.state;
      this.cells = dto.cells;
    });
  }
}
