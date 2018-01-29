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

Route::group(['prefix'=>'api/v1/users'],function (){
    // API
    Route::post('signin','apiUsersController@signin');
    Route::post('signup','apiUsersController@signup');
    Route::post('credits','apiUsersController@addCredits');
    Route::post('update','apiUsersController@update');
    Route::get('profile','apiUsersController@profile');
    Route::get('history','apiUsersController@history');
});

Route::group(['prefix'=>'api/v1/orders'],function () {
// orders
    Route::post('/registration', 'ordersController@newOrder');
});

//web browser pages
Route::group(['middleware' => 'web',],function (){

    Route::get('/', function () {
        return view('welcome');
    });


    Route::get('test','testController@get');
    Route::post('test','testController@post');

});
//TEST

