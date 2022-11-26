import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpErrorResponse
} from '@angular/common/http';
import { catchError, Observable, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { StorageService } from './services/storage.service';

@Injectable()
export class HttpOauthInterceptor implements HttpInterceptor {

  constructor(private storage: StorageService, private router: Router) {}

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    const credentials = this.storage.get('access_token');

    if (credentials) {
      return next.handle(request.clone({ setHeaders: { Authorization: 'Bearer ' + credentials } }))
        .pipe(
          catchError((response: HttpErrorResponse) => {
            if (response.status == 401) {
              this.storage.logoutUser()
              this.router.navigateByUrl(`/login`);
            }
            return throwError(response);
          }
          )
        )
    }
    else return next.handle(request);
  }
}