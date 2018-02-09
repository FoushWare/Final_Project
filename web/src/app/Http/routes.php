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
    Route::post('signin',   'Users\apiUsersController@signin');
    Route::post('signup',   'Users\apiUsersController@signup');
    Route::post('credits',  'Users\apiUsersController@addCredits');
    Route::post('update',   'Users\apiUsersController@update');
    Route::post('password', 'Users\apiUsersController@passwordUpdate');

    Route::get('profile',   'Users\apiUsersController@profile');
    Route::get('history',   'Users\apiUsersController@history');

    Route::get('logout',    'Users\apiUsersController@logout');
});

Route::group(['prefix'=>'api/v1/orders'],function () {
// orders
    Route::post('/registration', 'Orders/ordersController@newOrder');
});



//web browser pages
// TODO :: recover web middle ware
Route::group(['prefix' => '',],function (){

    Route::get('/', function () {
        return view('welcome');
    });


    Route::get('test','testController@get');
    Route::post('test','testController@post');

});
//TEST

