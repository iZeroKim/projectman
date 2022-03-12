<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateProjectsTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('projects', function (Blueprint $table) {
            $table->id("project_id");
            $table->string('name');
            $table->integer('total_cost');
            $table->integer('is_complete')->default(0);
            $table->unsignedBigInteger("admin_id");
            $table->timestamp('created_at')->default(now());
            $table->timestamp('completed_at')->nullable();
            $table->foreign('admin_id')->references('admin_id')->on('administrators');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::dropIfExists('projects');
    }
}
