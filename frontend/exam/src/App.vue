<!--
描述：vue主件
作者：211027137 林羽希
完善：211027134 叶怀生
创建：unkown
修改：2022年2月9日18:39:27
-->
<template>
  <div id="app">
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
body{
  background-color: #2b3248;
  /* 如果这边不增加背景色的话，在某些视图区较大的浏览器当中，底部会出现白边，但是并不能完全适配所有情况后好像 */
}
ul {
  list-style: none;
}
a {
  text-decoration: none;
}
* {
  margin: 0;
  padding: 0;
}
#app {
  font-family: "Microsoft YaHei", "Helvetica", "Tahoma", "Geneva", "Arial", sans-serif;
  background-color: #eee;
}
</style>
