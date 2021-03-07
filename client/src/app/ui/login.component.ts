import { Component, OnInit } from '@angular/core';
import { UserManager } from '../business/user-manager';
import { AppComponent } from '../app.component';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  constructor(private manager: UserManager,
    private app: AppComponent) { }

  ngOnInit(): void {
  }

  login(username: string,
      password: string) {
    this.manager.login(username, password)
      .subscribe(() => {
        this.app.authenticated = true;
      });
  }
}
