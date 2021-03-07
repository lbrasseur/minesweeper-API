import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserDto } from './dto/user-dto';
import { ResultDto } from './dto/result-dto';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  login(dto: UserDto): Observable<ResultDto<string>> {
    return this.http.post<ResultDto<string>>("/login", dto);
  }
}
