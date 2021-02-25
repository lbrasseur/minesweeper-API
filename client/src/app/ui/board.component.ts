import { Component, OnInit } from '@angular/core';
import { BoardManager } from '../business/board-manager';
import { BoardDto } from '../service/dto/board-dto';
import { CellDto } from '../service/dto/cell-dto';
import { CellState } from '../service/dto/cell-state';

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {
  currentBoard: string;
  cells: CellDto[][];

  constructor(private manager: BoardManager) { }

  ngOnInit(): void {
  }

  createBoard(width: number,
      height: number,
      mines: number) {
    this.manager.createBoard(width, height, mines)
      .subscribe((dto: BoardDto) => {
        this.currentBoard = dto.id;
        this.cells = dto.cells;
      });
  }

  clickCell(column: number,
      row: number) {
    this.manager.click(this.currentBoard, column, row)
      .subscribe((dto: BoardDto) => {
        this.cells = dto.cells;
      });
  }

  redFlagCell(column: number,
      row: number) {
    this.manager.redFlag(this.currentBoard, column, row)
      .subscribe((dto: BoardDto) => {
        this.cells = dto.cells;
      });
  }
}
