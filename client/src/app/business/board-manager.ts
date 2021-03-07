import { Injectable } from '@angular/core';
import { BoardService } from '../service/board-service';
import { ResultDto } from '../service/dto/result-dto';
import { BoardDto } from '../service/dto/board-dto';
import { BoardDataDto } from '../service/dto/board-data-dto';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class BoardManager {

  constructor(private service: BoardService) { }

  create(width: number,
      height: number,
      mines: number): Observable<BoardDto> {
    return this.extractPayload(this.service.create({
      width: width,
      height: height,
      mines: mines
    }));
  }

  pause(boardId: string): Observable<BoardDto> {
    return this.extractPayload(this.service.pause({
      boardId: boardId
    }));
  }

  resume(boardId: string): Observable<BoardDto> {
    return this.extractPayload(this.service.resume({
      boardId: boardId
    }));
  }

  find(): Observable<BoardDataDto[]> {
     return this.extractPayload(this.service.find());
  }

  delete(boardId: string): Observable<any> {
    return this.extractPayload(this.service.delete({
      boardId: boardId
    }));
  }

  click(boardId: string,
      column: number,
      row: number): Observable<BoardDto> {
    return this.extractPayload(this.service.click({
      boardId: boardId,
      column: column,
      row: row
    }));
  }

  redFlag(boardId: string,
      column: number,
      row: number): Observable<BoardDto> {
    return this.extractPayload(this.service.redFlag({
      boardId: boardId,
      column: column,
      row: row
    }));
  }

  questionMark(boardId: string,
      column: number,
      row: number): Observable<BoardDto> {
    return this.extractPayload(this.service.questionMark({
      boardId: boardId,
      column: column,
      row: row
    }));
  }

  initial(boardId: string,
      column: number,
      row: number): Observable<BoardDto> {
    return this.extractPayload(this.service.initial({
      boardId: boardId,
      column: column,
      row: row
    }));
  }

  private  extractPayload<T>(operation: Observable<ResultDto<T>>): Observable<T> {
    return operation.pipe(map(result => {
      if (result.error != null) {
        alert(result.error); // TODO: implement nicer error reporting
        throw result.error;
      } else {
        return result.payload;
      }
    }));
  }
}
