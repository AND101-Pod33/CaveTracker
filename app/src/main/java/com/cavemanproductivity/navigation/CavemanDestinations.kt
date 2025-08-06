package com.cavemanproductivity.navigation

sealed class CavemanDestinations(val route: String) {
    object Dashboard : CavemanDestinations("cave_dashboard")
    object HabitCave : CavemanDestinations("habit_cave")
    object TaskRocks : CavemanDestinations("task_rocks")
    object MoonCalendar : CavemanDestinations("moon_calendar")
    object FireFocus : CavemanDestinations("fire_focus")
    object Stats : CavemanDestinations("stats")
    object ChiefSettings : CavemanDestinations("chief_settings")
    
    // Detail screens
    object HabitDetail : CavemanDestinations("habit_detail/{habitId}") {
        fun createRoute(habitId: String) = "habit_detail/$habitId"
    }
    object TaskDetail : CavemanDestinations("task_detail/{taskId}") {
        fun createRoute(taskId: String) = "task_detail/$taskId"
    }
    object AddEditHabit : CavemanDestinations("add_edit_habit?habitId={habitId}") {
        fun createRoute(habitId: String? = null) = 
            if (habitId != null) "add_edit_habit?habitId=$habitId" else "add_edit_habit"
    }
    object AddEditTask : CavemanDestinations("add_edit_task?taskId={taskId}") {
        fun createRoute(taskId: String? = null) = 
            if (taskId != null) "add_edit_task?taskId=$taskId" else "add_edit_task"
    }
}