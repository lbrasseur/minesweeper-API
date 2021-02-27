import { Component, OnInit } from '@angular/core';
import { BoardManager } from '../business/board-manager';
import { BoardDto } from '../service/dto/board-dto';
import { BoardState } from '../service/dto/board-state';
import { CellDto } from '../service/dto/cell-dto';
import { CellState } from '../service/dto/cell-state';

import { Observable } from 'rxjs';

const HOUR_MINUTE_RATIO = 60;
const MINUTE_SECOND_RATIO = 60;
const SECOND_MILLI_RATIO = 1000;

@Component({
  selector: 'app-board',
  templateUrl: './board.component.html',
  styleUrls: ['./board.component.css']
})
export class BoardComponent implements OnInit {
  boardId: string;
  boardState: BoardState;
  cells: CellDto[][];
  playingTime: number;
  formattedPlayingTime: string;

  constructor(private manager: BoardManager) { }

  ngOnInit(): void {
  setInterval(() => {
      if (this.isPlaying() && this.playingTime != null) {
        this.playingTime += SECOND_MILLI_RATIO;
        this.updatePlayingTime();
      }
    }, SECOND_MILLI_RATIO);
  }

  createBoard(width: number,
      height: number,
      mines: number) {
    this.playingTime = null;
    this.updateBoard(this.manager.createBoard(width, height, mines));
  }

  pause() {
    this.playingTime = null;
    this.updateBoard(this.manager.pause(this.boardId));
  }

  resume() {
    this.updateBoard(this.manager.resume(this.boardId));
  }

  isPaused() : boolean {
    return this.boardState == BoardState.PAUSED;
  }

  isPlaying() : boolean {
    return this.boardState == BoardState.PLAYING;
  }

  isVisible() : boolean {
    return this.boardState == BoardState.PLAYING
      ||  this.boardState == BoardState.SOLVED
      ||  this.boardState == BoardState.EXPLODED;
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
      if (this.playingTime == null) {
        this.playingTime = dto.playingTime;
        this.updatePlayingTime();
      }
    });
  }

  private updatePlayingTime() {
    var seconds = Math.floor(this.playingTime / SECOND_MILLI_RATIO);
    var minutes = Math.floor(seconds / MINUTE_SECOND_RATIO);
    var hours = Math.floor(minutes / HOUR_MINUTE_RATIO);
    seconds -= minutes * MINUTE_SECOND_RATIO;
    minutes -= hours * HOUR_MINUTE_RATIO;

    let date = new Date(Date.UTC(0, 0, 0, hours, minutes, seconds));
    new Date();
    this.formattedPlayingTime = `${pad(hours)}:${pad(minutes)}:${pad(seconds)}`;
  }
}

function pad(num) {
    let s = num + "";
    while (s.length < 2) s = "0" + s;
    return s;
}
