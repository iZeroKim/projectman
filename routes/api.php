<?php

use App\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/


    Route::get('/user', function (Request $request) {
        return $request->user();
    });


    Route::get('/users', function(){
        return User::all();
    });

    //Projects
    Route::get('projects', 'ProjectController@index')->middleware('auth:api');
    Route::get('projects/{id}', 'ProjectController@show')->middleware('auth:api');
    Route::post('projects', 'ProjectController@store')->middleware('auth:api');
    Route::put('projects/{id}', 'ProjectController@update')->middleware('auth:api');
    Route::delete('projects/{id}', 'ProjectController@destroy')->middleware('auth:api');

    //Contractors
    Route::get('contractors', 'ContractorController@index')->middleware('auth:api');
    Route::get('contractors/{id}', 'ContractorController@show')->middleware('auth:api');
    Route::post('contractors', 'ContractorController@store')->middleware('auth:api');
    Route::put('contractors/{id}', 'ContractorController@update')->middleware('auth:api');
    Route::delete('contractor/{id}', 'ContractorController@destroy')->middleware('auth:api');




//Administrators
Route::post('register', 'UsersController@register');
Route::post('login', 'UsersController@login');


