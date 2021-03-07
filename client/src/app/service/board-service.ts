import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CreateBoardDto } from './dto/create-board-dto';
import { ResultDto } from './dto/result-dto';
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

  create(dto: CreateBoardDto): Observable<ResultDto<BoardDto>> {
    return this.http.post<ResultDto<BoardDto>>("/create", dto);
  }

  pause(dto: BoardIdDto): Observable<ResultDto<BoardDto>> {
    return this.http.post<ResultDto<BoardDto>>("/pause", dto);
  }

  resume(dto: BoardIdDto): Observable<ResultDto<BoardDto>> {
    return this.http.post<ResultDto<BoardDto>>("/resume", dto);
  }

  find(): Observable<ResultDto<BoardDataDto[]>> {
    return this.http.get<ResultDto<BoardDataDto[]>>("/find");
  }

  delete(dto: BoardIdDto): Observable<ResultDto<any>> {
    return this.http.post<ResultDto<any>>("/delete", dto);
  }

  click(dto: CellIdDto): Observable<ResultDto<BoardDto>> {
    return this.http.post<ResultDto<BoardDto>>("/click", dto);
  }

  redFlag(dto: CellIdDto): Observable<ResultDto<BoardDto>> {
    return this.http.post<ResultDto<BoardDto>>("/redFlag", dto);
  }

  questionMark(dto: CellIdDto): Observable<ResultDto<BoardDto>> {
    return this.http.post<ResultDto<BoardDto>>("/questionMark", dto);
  }

  initial(dto: CellIdDto): Observable<ResultDto<BoardDto>> {
    return this.http.post<ResultDto<BoardDto>>("/initial", dto);
  }
}
