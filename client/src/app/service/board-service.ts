import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CreateBoardDto } from './dto/create-board-dto';
import { CellIdDto } from './dto/cell-id-dto';
import { BoardIdDto } from './dto/board-id-dto';
import { BoardDataDto } from './dto/board-data-dto';
import { BoardDto } from './dto/board-dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BoardService {

  constructor(private http: HttpClient) { }

  create(dto: CreateBoardDto): Observable<BoardDto> {
    return this.http.post<BoardDto>("/create", dto);
  }

  pause(dto: BoardIdDto): Observable<BoardDto> {
    return this.http.post<BoardDto>("/pause", dto);
  }

  resume(dto: BoardIdDto): Observable<BoardDto> {
    return this.http.post<BoardDto>("/resume", dto);
  }

  find(): Observable<BoardDataDto[]> {
    return this.http.get<BoardDataDto[]>("/find");
  }

  delete(dto: BoardIdDto): Observable<any> {
    return this.http.post<BoardIdDto>("/delete", dto);
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
