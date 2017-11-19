<?php

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| Here is where you can register all of the routes for an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/

Route::get('/', function () {
    return view('welcome');
});

// API
Route::post('/api/v1/users/signin','apiUsersController@signin');
Route::post('/api/v1/users/signup','apiUsersController@signup');
Route::post('/api/v1/users/credits','apiUsersController@addCredits');
Route::post('/api/v1/users/update','apiUsersController@update');
Route::get('/api/v1/users/profile','apiUsersController@profile');
Route::get('/api/v1/users/history','apiUsersController@history');

//TEST


Route::get('/test','apiUsersController@test');
Route::post('/test','apiUsersController@test');
