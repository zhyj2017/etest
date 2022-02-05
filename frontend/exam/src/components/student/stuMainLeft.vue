<!--
描述：学生端主页，左侧导航组件
作者：211027134 叶怀生
时间：2022年1月30日16:45:45
-->
<template>
  <div id="left">
    <!-- [WARNING]这里通过flag标志实现的侧边栏收缩有点卡顿-->
    <el-menu
      active-text-color="#ffce7b"
      text-color="#fffffb"
      :default-active="this.$route.path"
      class="el-menu-vertical-demo"
      @open="handleOpen"
      @close="handleClose"
      :collapse="flag"
      background-color="#472d56"
      menu-trigger="click" router>
      <el-submenu v-for="(item,index) in stuMenu" :index='item.index' :key="index">
        <template slot="title">
          <div class="left-width">
            <i class="iconfont" :class="item.icon"></i>
            <span slot="title" class="title">{{item.title}}</span>

          </div>
        </template>
        <el-menu-item-group v-for="(list,index1) in item.content" :key="index1">
          <el-menu-item @click="handleTitle(item.index)" :index="list.path">{{list.item}}</el-menu-item>
          <!-- [WARNING]这里在展开成绩管理时会卡顿，有点莫名其妙…… -->
        </el-menu-item-group>
      </el-submenu>
    </el-menu>
  </div>
</template>

<script>
import store from '@/vuex/store'
import {mapState} from 'vuex'
export default {
  name: "mainLeft",
  data() {
    return {

    }
  },
  computed: mapState(["flag","stuMenu"]),
  created() {
    this.addData()
  },
  methods: {
    handleOpen(key, keyPath) {
      // console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      // console.log(key, keyPath);
    },
    //点击标题传递参数给navigator组件
    handleTitle(index) {
      // [WARNING]此处使用额外定义的getNameByStr函数解决学生端顶部标题错误的问题
      this.bus.$emit('sendNameByStr',index, this.stuMenu[index-1])
    }
    //[WARNING]这里通过在created挂钩可以将本组件和common中的合并
    /*
    在导出属性中新增created方法
    created() {
      this.roleAdaptive()
    },
    并在roleAdaptive方法中根据角色类型判断，加载不同的Menu
    addData() {
      let role = this.$cookies.get("role")
      if(role == 学生) {
        当前Menu=学生Menu;
      }
    }
    * */
  },
  store
}
</script>

<style>
.el-menu-vertical-demo .el-submenu__title {
  overflow: hidden;
}
.left-width .iconfont {
  font-size: 18px;
  color: #fff;
}
.left-width {
  width: 213px;
}
.el-menu-vertical-demo:not(.el-menu--collapse) {
  min-height: 900px;
}
#left {
  height: 900px;
  background-color: #472d56;
  z-index: 0;
}
#left .el-menu-vertical-demo .title {
  color: #fff;
  font-size: 16px;
  font-weight: bold;
  margin-left: 14px;
}
.el-submenu {
  border-bottom: 1px solid #faf1f10f !important;
}
.el-submenu__title:hover {
  background-color: #fff;
}
.el-submenu__title i {
    color: #fbfbfc !important;
}
</style>
