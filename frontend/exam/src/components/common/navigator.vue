<template>
  <div id="nav">
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item class="title">{{active.title}}</el-breadcrumb-item>
      <el-breadcrumb-item v-if="active.item1 != null">{{active.item1}}</el-breadcrumb-item>
      <el-breadcrumb-item v-if="active.item2 != null">{{active.item2}}</el-breadcrumb-item>
      <el-breadcrumb-item v-if="active.item3 != null">{{active.item3}}</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
import store from '@/vuex/store'
import {mapState} from 'vuex'
export default {
  data() {
    return {
      active: [],
      index1: null,
    }
  },
  computed: mapState(["menu"]),
  methods: {
    getIndex() {
      this.bus.$on('sendIndex',(data)=>{
        this.index1 = data
        this.active = this.menu[data-1]
        // console.log(JSON.stringify(this.active)+'----')
      })
    },
    // [WARNING]此处使用额外定义的getNameByStr函数解决学生端顶部标题错误的问题
    // 但这样，一定程度上增加了耦合度，应当考虑根据角色修改menu内容，使用同一个函数完成顶部标题更改
    // 此外，当刷新页面时，由于没有index传入，所以顶部标题为空白，这是一个小瑕疵
    getNameByStr() {
      this.bus.$on('sendNameByStr',(data, name)=>{
        if(data >= 0)
          this.index1 = data;
        this.active = name;
      })
    }
  },
  created() {
      this.getIndex(),
      this.getNameByStr()
  },
  beforeDestroy() {
    // this.bus.$off('sendIndex') //销毁
  },
  store
}
</script>

<style scoped>
#nav .el-breadcrumb {
  height: 60px;
  line-height: 60px;
  padding-left: 20px;
}
#nav .el-breadcrumb .title{
  font-weight: bold;
}
</style>
