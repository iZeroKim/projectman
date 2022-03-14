<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Project extends Model
{
    protected $fillable = [
        'name',
        'description',
        'total_cost',
        'is_complete',
        'admin_id'
    ];


}
