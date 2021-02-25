import { Injectable } from '@angular/core';
import { BoardService } from '../service/board-service';
import { BoardDto } from '../service/dto/board-dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class BoardManager {

  constructor(private service: BoardService) { }

  createBoard(width: number, height: number, mines: number): Observable<BoardDto> {
    return this.service.createBoard({
      width: width,
      height: height,
      mines: mines
    });
  }
}
