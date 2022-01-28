import VUE from 'vue'
import VUEX from 'vuex'

VUE.use(VUEX)

const state = {
  isPractice: false, //练习模式标志
  flag: false, //菜单栏左右滑动标志
  userInfo: null,
  menu: [{
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
      content:[{item1:'学生管理',path:'/studentManage'},{item2: '添加学生',path: '/addStudent'}],
    },
  ],
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
