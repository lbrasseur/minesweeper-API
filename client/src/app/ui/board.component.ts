import { Component, OnInit } from '@angular/core';
import { BoardManager } from '../business/board-manager';
import { BoardDto } from '../service/dto/board-dto';
import { BoardDataDto } from '../service/dto/board-data-dto';
import { BoardState } from '../service/dto/board-state';
import { CellDto } from '../service/dto/cell-dto';
import { CellState } from '../service/dto/cell-state';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

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
  boards: BoardDataDto[];

  constructor(private manager: BoardManager) { }

  ngOnInit(): void {
    setInterval(() => {
      if (this.isPlaying() && this.playingTime != null) {
        this.playingTime += SECOND_MILLI_RATIO;
        this.updatePlayingTime();
      }
    }, SECOND_MILLI_RATIO);
    this.updateBoardList();
  }

  create(width: number,
      height: number,
      mines: number) {
    this.playingTime = null;
    this.updateBoard(this.updateListOnFinish(this.manager.create(width, height, mines)));
  }

  pause() {
    this.playingTime = null;
    this.updateBoard(this.updateListOnFinish(this.manager.pause(this.boardId)));
  }

  resume() {
    this.resumeBoard(this.boardId);
  }

  isPaused() : boolean {
    return this.isPausedState(this.boardState);
  }

  isPausedState(state: BoardState) : boolean {
    return state == BoardState.PAUSED;
  }

  isPlaying() : boolean {
    return this.boardState == BoardState.PLAYING;
  }

  isVisible() : boolean {
    return this.boardState == BoardState.PLAYING
      ||  this.boardState == BoardState.SOLVED
      ||  this.boardState == BoardState.EXPLODED;
  }

  isCurrentBoard(boardId: string) : boolean {
    return this.boardId == boardId
      && this.boardState == BoardState.PLAYING;
  }

  delete(boardId: string) {
    if (confirm("Are you sure?")) {
      this.manager.delete(boardId)
        .subscribe((dto: BoardDto) => {
          this.updateBoardList();
        });
    }
  }

  switch(boardId: string) {
    if (this.boardState == BoardState.PLAYING) {
      this.manager.pause(this.boardId)
        .subscribe((dummy: BoardDto) => {
          this.resumeBoard(boardId);
        });
    } else {
     this.resumeBoard(boardId);
    }
  }

  updateBoardList() {
    this.manager.find()
      .subscribe((boards: BoardDataDto[]) => {
        this.boards = boards;
      });
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

  private resumeBoard(boardId: string) {
    this.playingTime = null;
    this.updateBoard(this.updateListOnFinish(this.manager.resume(boardId)));
  }

  private updateBoard(operation: Observable<BoardDto>) {
    operation.subscribe((dto: BoardDto) => {
      this.boardId = dto.id;
      if (this.boardState != dto.state) {
        this.boardState = dto.state;
        this.updateBoardList();
      }
      this.cells = dto.cells;
      if (this.playingTime == null) {
        this.playingTime = dto.playingTime;
        this.updatePlayingTime();
      }
    });
  }

  private updateListOnFinish(operation: Observable<BoardDto>): Observable<BoardDto> {
    return operation.pipe(map((board: BoardDto) => {
        this.updateBoardList();
        return board;
      }));
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
