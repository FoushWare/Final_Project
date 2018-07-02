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
    Route::post('/registration', 'Orders\ordersController@saveToken');
});

Route::get('qr-code', function () 
{
  return QRCode::text('QR Code Generator for Laravel!')->svg();
});

/**
 * Ras end Points
 */
Route::group(['prefix'=>'/ras'],function () {
// orders
    Route::post('in',      'Raspberry\raspberryController@checkIn');
    Route::post('user',      'Raspberry\raspberryController@user');
    Route::post('item',    'Raspberry\raspberryController@item');
    Route::post('out',      'Raspberry\raspberryController@checkOut');

});




Route::group(['prefix' => '','middleware' => 'web'],function (){
    //public Routes
    Route::get('/', function () {
        return view('welcome');
    });

    Route::get('signup',    'Front\publicController@getSignUp');
    Route::post('signup',   'Front\publicController@postSignUp');

    Route::get('signin',    'Front\publicController@getSignIn');
    Route::post('signin',   'Front\publicController@postSignIn');


    Route::get('forgot',    'Front\publicController@getForgot');
    Route::post('forgot',   'Front\publicController@postForgot');

    Route::get('/reset',function (){
        return view('public.reset');
    });

    Route::get('QRLogin',function (){
        return "<h1>Coming Soon</h1>";
    });
});

//TEST
Route::get('test','testController@get');
Route::post('test','testController@post');
