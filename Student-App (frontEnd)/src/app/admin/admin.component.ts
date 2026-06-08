
import { Component, inject, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../services/auth.service';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  users: any[] = [];
  loading: boolean = false;
  error: string = '';
  success: string = '';
  
  // 🔥 Search property
  searchEmail: string = '';
  
  // Delete User Properties
  showDeleteModal: boolean = false;
  deleteUserId: string = '';
  deleting: boolean = false;
  
  // Create User Form
  newUser = {
    name: '',
    email: '',
    password: '',
    roles: [{ id: 3 }]
  };
  
  private http = inject(HttpClient);
  private auth = inject(AuthService);
  private router = inject(Router);
  
  ngOnInit() {
    this.loadUsers();
  }
  
  // Load all users
 loadUsers() {
  this.loading = true;
  this.searchEmail = '';
  this.http.get('http://localhost:8083/users/getAllUsers')
    .subscribe({
      next: (res: any) => {
        // 🔥 Check - agar response mein 'users' array hai toh
        this.users = res.users || res;
        this.loading = false;
      },
      error: (err) => {
        console.error('Error:', err);
        this.error = 'Failed to load users';
        this.loading = false;
      }
    });

  }
  
  // 🔥 Search users by email
searchUsers() {
  if (!this.searchEmail.trim()) {
    this.loadUsers();
    return;
  }
  
  this.loading = true;
  this.error = '';
  
  this.http.get(`http://localhost:8083/users/search?email=${this.searchEmail}`)
    .subscribe({
      next: (res: any) => {
        console.log('Response:', res);
        
        // 🔥 SIRF YEH LINE CHANGE KARO
        this.users = res.users || [];  // 'users' property use karo
        
        this.loading = false;
        
        if (this.users.length === 0) {
          this.error = `No users found with email containing "${this.searchEmail}"`;
          setTimeout(() => this.error = '', 3000);
        }
      },
      error: (err) => {
        console.error('Error:', err);
        this.error = 'Failed to search users';
        this.loading = false;
        setTimeout(() => this.error = '', 3000);
      }
    });
}

  // Reset search
  resetSearch() {
    this.searchEmail = '';
    this.loadUsers();
  }
  
  onRoleChange(roleName: string) {
    let roleId = 3;
    if (roleName === 'ADMIN') {
      roleId = 1;
    } else if (roleName === 'TEACHER') {
      roleId = 2;
    } else {
      roleId = 3;
    }
    this.newUser.roles = [{ id: roleId }];
  }
  
 
  createUser() {
  if (!this.newUser.name) {
    this.error = 'Name is required';
    return;
  }
  if (!this.newUser.email) {
    this.error = 'Email is required';
    return;
  }
  if (!this.newUser.password) {
    this.error = 'Password is required';
    return;
  }
  
  this.loading = true;
  this.error = '';
  this.success = '';
  
  const userData = {
    name: this.newUser.name,
    email: this.newUser.email,
    password: this.newUser.password,
    roles: this.newUser.roles
  };
  
  console.log('Creating user:', userData);
  
  this.http.post('http://localhost:8083/auth/register', userData)
    .subscribe({
      next: (res: any) => {
        console.log('User created response:', res);
        this.success = `User ${this.newUser.name} (${this.newUser.email}) created successfully!`;
        
        // Reset form
        this.newUser = { name: '', email: '', password: '', roles: [{ id: 3 }] };
        
        // 🔥 Reload users
        this.loadUsers();  // This will set loading = true and then false
        
        // Don't set loading = false here - loadUsers will handle it
        setTimeout(() => this.success = '', 3000);
      },
      error: (err) => {
        console.error('Error:', err);
        this.error = err.error?.message || 'Failed to create user';
        this.loading = false;  // Only set false on error
        setTimeout(() => this.error = '', 3000);
      }
    });
}
  
  openDeleteModal() {
    this.deleteUserId = '';
    this.showDeleteModal = true;
  }
  
  closeDeleteModal() {
    this.showDeleteModal = false;
    this.deleteUserId = '';
    this.error = '';
  }
  
  deleteUser() {
    if (!this.deleteUserId) {
      this.error = 'Please enter User ID';
      return;
    }
    
    this.deleting = true;
    this.error = '';
    
    this.http.delete(`http://localhost:8083/users/${this.deleteUserId}`, {
      responseType: 'text'
    }).subscribe({
      next: (response: string) => {
        console.log('User deleted response:', response);
        this.success = `User with ID ${this.deleteUserId} deleted successfully!`;
        this.showDeleteModal = false;
        this.deleteUserId = '';
        this.loadUsers();
        this.deleting = false;
        setTimeout(() => this.success = '', 3000);
      },
      error: (err) => {
        console.error('Error:', err);
        if (err.status === 200 || err.status === 204 || err.status === 404) {
          this.success = `User with ID ${this.deleteUserId} deleted successfully!`;
          this.showDeleteModal = false;
          this.deleteUserId = '';
          this.loadUsers();
        } else {
          this.error = err.error?.message || `Failed to delete user with ID ${this.deleteUserId}`;
        }
        this.deleting = false;
        setTimeout(() => this.success = '', 3000);
        setTimeout(() => this.error = '', 3000);
      }
    });
  }
  
  goToTeacherAssign() {
  this.router.navigate(['/teacher-assign']);
}
  goToTimeTable() {
  this.router.navigate(['/timetable']);
}

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }

  goToAssignSlot() {
  this.router.navigate(['/assign-slot']);
}
}