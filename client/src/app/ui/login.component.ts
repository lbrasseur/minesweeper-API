import { Component, OnInit } from '@angular/core';
import { UserManager } from '../business/user-manager';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  constructor(private manager: UserManager) { }

  ngOnInit(): void {
  }

  login(username: string,
      password: string) {
    this.manager.login(username, password)
      .subscribe((token: string) => {
      });
  }
}
