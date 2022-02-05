<!--
描述：学生端组件，成绩管理功能描述页面
作者：211027134 叶怀生
时间：2022年2月5日13:52:55
-->
<template>
  <section class="description">
    <p class="title">成绩管理功能介绍</p>
    <p class="content">在本功能中，您可在【成绩查询】中查看已完成考试的得分，而在【成绩排名】中可查看每次考试中的排名情况，注意该排名可能随着新加入考试的同学而发生动态的变化。
    </p>
  </section>
</template>

<style lang="scss" scoped>
.description {
  margin-left: 40px;
  .title {
    font-size: 22px;
    font-weight: 400;
    color: rgb(31, 47, 61);
  }
  .content {
    width: 600px;
    background-color: #FAF5F2;
    padding: 16px 32px;
    border-radius: 4px;
    border-left: 5px solid #FDC8C8;
    margin: 20px 0px;
  }
}
</style>
