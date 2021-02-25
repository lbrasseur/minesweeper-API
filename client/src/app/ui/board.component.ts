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
  width: number = 10;
  height: number = 7;
  mines: number = 9;

  cells: CellDto[][];

  constructor(private manager: BoardManager) { }

  ngOnInit(): void {
  }

  createBoard() {
    this.manager.createBoard(this.width, this.height, this.mines)
      .subscribe((dto: BoardDto) => {
        this.cells = dto.cells;
      });
  }
}
