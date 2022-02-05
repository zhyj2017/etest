<!--
描述：学生端组件，考试信息功能描述页面
作者：211027134 叶怀生
创建：2022年2月5日13:52:15
修改：2022年2月6日05:26:32
-->
<template>
  <section class="description">
    <p class="title">考试信息功能介绍</p>
    <p class="content">在本功能中，您可在【待参加考试】中查看您未完成的考试项目，而在【已完成考试】中可查看已完成考试的得分以及答题细节。
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
    border-left: 5px solid rgb(80, 191, 255);
    margin: 20px 0px;
  }
}
</style>
