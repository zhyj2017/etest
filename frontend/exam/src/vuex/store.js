import VUE from 'vue'
import VUEX from 'vuex'

VUE.use(VUEX)

const state = {
  isPractice: false, //练习模式标志
  flag: false, //菜单栏左右滑动标志
  userInfo: null,
  menu: [
    {
      index: '1',
      title: '考试管理',
      icon: 'icon-kechengbiao',
      content:[{item1:'功能介绍',path:'/examDescription'},{item2:'考试查询',path:'/selectExam'},{item3:'添加考试',path:'/addExam'}],
    },
    {
      index: '2',
      title: '试卷管理',
      icon: 'icon-tiku',
      content:[{item1:'功能介绍',path:'/answerDescription'},{item2:'所有试卷',path:'/selectAnswer'},{item3:'增加试卷',path:'/addAnswer'}],
    },
    {
      index: '3',
      title: '题目管理',
      icon: 'el-icon-s-order',
      content:[{item1:'题目查询',path:'/selectQuestion'},{item2:'增加题目',path:'/addQuestion'}],
    },
    {
      index: '4',
      title: '成绩查询',
      icon: 'icon-performance',
      content:[{item1:'学生成绩查询',path:'/allStudentsGrade'}],
    },
    {
      index: '5',
      title: '学生管理',
      icon: 'icon-role',
      content: [{item1:'学生管理',path:'/studentManage'},{item2: '班级管理',path: '/classManage'}]
      // content:[{item1:'学生管理',path:'/studentManage'},{item2: '添加学生',path: '/addStudent'},{item3: '班级管理',path: '/classManage'},{item4: '创建班级',path: '/addClass'}],
      // 由于“添加学生”、“创建班级”两个功能归类到了“学生管理”、“班级管理”页面当中，所以这里只有两个子页面。
    },
  ],
  stuMenu: [
    {
      index: '1',
      title: '考试信息',
      icon: 'icon-kechengbiao',
      content:[{item:'功能介绍',path:'/stuExamDescription'},{item:'待参加考试',path:'/stuNewExam'},{item:'已完成考试',path:'/stuCompletedExam'}],
    },
    {
      index: '2',
      title: '成绩管理',
      icon: 'icon-performance',
      content:[{item:'功能介绍',path:'/stuScoreDescription'},{item:'成绩查询',path:'/stuScoreCheck'},{item:'成绩排名',path:'/stuScoreRank'}],
    }
  ]
}
const mutations = {
  practice(state,status) {
    state.isPractice = status
  },
  toggle(state) {
    state.flag = !state.flag
  },
  changeUserInfo(state,info) {
    state.userInfo = info
  }
}
const getters = {

}
const actions = {
  getUserInfo(context,info) {
    context.commit('changeUserInfo',info)
  },
  getPractice(context,status) {
    context.commit('practice',status)
  }
}
export default new VUEX.Store({
  state,
  mutations,
  getters,
  actions,
  // store
})
