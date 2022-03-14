<?php

use Illuminate\Support\Facades\Schema;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateContractorProjectTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('contractor_project', function (Blueprint $table) {
            $table->unsignedInteger('contractor_id');
            $table->unsignedInteger('project_id');
            $table->timestamps();
            $table->timestamp('completed_at')->nullable();
            $table->foreign('contractor_id')->references('id')->on('contractors');
            $table->foreign('project_id')->references('id')->on('projects');
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
