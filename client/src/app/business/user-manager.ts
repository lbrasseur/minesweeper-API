import { Injectable } from '@angular/core';
import { UserService } from '../service/user-service';
import { UserDto } from '../service/dto/user-dto';
import { extractPayload } from './manager-utils';

import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class UserManager {
  private token: string;

  constructor(private service: UserService) { }

  login(username: string,
      password: string): Observable<any> {
    return extractPayload(this.service.login({
      username: username,
      password: password
    }))
    .pipe(map((token) => {
      this.token = token;
      return null;
    }));
  }

  isAuthenticated(): boolean {
    return this.token != null;
  }

  getToken(): string {
    return this.token;
  }
}
