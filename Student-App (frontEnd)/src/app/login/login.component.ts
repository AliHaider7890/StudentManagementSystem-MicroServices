import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  email = '';
  password = '';
  error = '';
  
  private auth = inject(AuthService);
  private router = inject(Router);

  login() {
    if (!this.email || !this.password) {
      this.error = 'Please enter email and password';
      return;
    }

    this.auth.login({ email: this.email, password: this.password })
      .subscribe({
        next: (res: any) => {
          console.log('Login response:', res);
          
          if (res.token) {
            localStorage.setItem('token', res.token);
            
            // Store ID
            if (res.Id) {
              localStorage.setItem('Id', res.Id);
              console.log('User ID stored:', res.Id);
            }
            
            // Store Email and Role
            localStorage.setItem('userEmail', res.email);
            localStorage.setItem('userRole', res.role);
            console.log('Logged in Email:', res.email);
            console.log('User Role:', res.role);
            
            // 🔥 Redirect based on role
            if (res.role === 'ADMIN') {
              this.router.navigate(['/admin']);
            } 
            else if (res.role === 'TEACHER') {
              this.router.navigate(['/teacher']);
            }
            else {
              this.router.navigate(['/dashboard']);
            }
          } else {
            this.error = 'No token received';
          }
        },
        error: (err) => {
          console.error('Login error:', err);
          this.error = err.error?.message || 'Login failed';
        }
      });
  }
}