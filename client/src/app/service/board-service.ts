import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CreateBoardDto } from './dto/create-board-dto';
import { CellIdDto } from './dto/cell-id-dto';
import { BoardDto } from './dto/board-dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BoardService {

  constructor(private http: HttpClient) { }

  createBoard(dto: CreateBoardDto): Observable<BoardDto> {
    return this.http.post<BoardDto>("/create", dto);
  }

  click(dto: CellIdDto): Observable<BoardDto> {
    return this.http.post<BoardDto>("/click", dto);
  }

  redFlag(dto: CellIdDto): Observable<BoardDto> {
    return this.http.post<BoardDto>("/redFlag", dto);
  }

  questionMark(dto: CellIdDto): Observable<BoardDto> {
    return this.http.post<BoardDto>("/questionMark", dto);
  }

  initial(dto: CellIdDto): Observable<BoardDto> {
    return this.http.post<BoardDto>("/initial", dto);
  }
}
