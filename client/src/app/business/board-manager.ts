import { Injectable } from '@angular/core';
import { BoardService } from '../service/board-service';
import { BoardDto } from '../service/dto/board-dto';
import { BoardDataDto } from '../service/dto/board-data-dto';
import { extractPayload } from './manager-utils';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BoardManager {

  constructor(private service: BoardService) { }

  create(width: number,
      height: number,
      mines: number): Observable<BoardDto> {
    return extractPayload(this.service.create({
      width: width,
      height: height,
      mines: mines
    }));
  }

  pause(boardId: string): Observable<BoardDto> {
    return extractPayload(this.service.pause({
      boardId: boardId
    }));
  }

  resume(boardId: string): Observable<BoardDto> {
    return extractPayload(this.service.resume({
      boardId: boardId
    }));
  }

  find(): Observable<BoardDataDto[]> {
     return extractPayload(this.service.find());
  }

  delete(boardId: string): Observable<any> {
    return extractPayload(this.service.delete({
      boardId: boardId
    }));
  }

  click(boardId: string,
      column: number,
      row: number): Observable<BoardDto> {
    return extractPayload(this.service.click({
      boardId: boardId,
      column: column,
      row: row
    }));
  }

  redFlag(boardId: string,
      column: number,
      row: number): Observable<BoardDto> {
    return extractPayload(this.service.redFlag({
      boardId: boardId,
      column: column,
      row: row
    }));
  }

  questionMark(boardId: string,
      column: number,
      row: number): Observable<BoardDto> {
    return extractPayload(this.service.questionMark({
      boardId: boardId,
      column: column,
      row: row
    }));
  }

  initial(boardId: string,
      column: number,
      row: number): Observable<BoardDto> {
    return extractPayload(this.service.initial({
      boardId: boardId,
      column: column,
      row: row
    }));
  }
}
