import Vue from 'vue'
import Router from 'vue-router'
Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'index', //登录界面
      component: () => import('@/components/admin/index')
    },
    {
      path: '/index', //教师主页
      component: () => import('@/components/admin/index'),
      children: [
        {
          path: '/examDescription', //考试管理功能描述
          component: () => import('@/components/teacher/examDescription')
        },
        {
          path: '/selectExam', //查询所有考试
          component: () => import('@/components/teacher/selectExam')
        },
        {
          path: '/selectQuestion', //查询所有问题
          component: () => import('@/components/teacher/selectQuestion')
        },
        {
          path: '/addQuestion', //查询所有考试
          component: () => import('@/components/teacher/addQuestion')
        },
        {
          path: '/addExam', //添加考试
          component: () => import('@/components/teacher/addExam')
        },
        {
          path: '/answerDescription', //题库管理功能介绍
          component: ()=> import('@/components/teacher/answerDescription')
        },
        {
          path: '/selectAnswer', //查询所有题库
          component: () => import('@/components/teacher/selectAnswer')
        },
        {
          path: '/addAnswer', //增加题库主界面
          component: () => import('@/components/teacher/addAnswer')
        },
        {
          path: '/allStudentsGrade', //查看所有成绩
          component: () => import('@/components/teacher/allStudentsGrade')
        },
        {
          path: '/studentManage', //学生管理界面
          component: () => import('@/components/teacher/studentManage')
        },
        {
          path: '/addStudent', //添加学生
          component: () => import('@/components/teacher/addStudent')
        },
        {
          path: '/classManage', //班级管理
          component: () => import('@/components/teacher/classManage')
        },
        {
          path: '/addClass', //创建班级
          component: () => import('@/components/teacher/addClass')
        }
      ]
    },
  ]
})
