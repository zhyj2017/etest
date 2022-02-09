<!--
描述：学生端组件，查看未完成的考试列表
作者：211027134 叶怀生
创建：2022年2月5日14:47:56
修改：2022年2月10日01:41:56
-->
<template>
  <div class="exam">
    <el-table :data="pagination.records" border :row-class-name="tableRowClassName">
      <el-table-column fixed="left" prop="tname" label="考试名称" width="240"></el-table-column>
      <el-table-column prop="starttime" label="考试开始时间" width="200"></el-table-column>
      <el-table-column prop="endtime" label="考试结束时间" width="200"></el-table-column>
      <el-table-column prop="description" label="考试描述" width="550"></el-table-column>
      <!--
        <el-table-column prop="author" label="发布人" width="150"></el-table-column>
      -->
      <el-table-column prop="padding" label="" width="150"></el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button @click="entryExam(scope.row.id, scope.row.pid)" type="primary" size="small" icon="el-icon-edit-outline">进入考试</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pagination.current"
      :page-sizes="[6, 10]"
      :page-size="pagination.size"
      layout="total, sizes, prev, pager, next, jumper"
      :total="pagination.total"
      class="page"
    ></el-pagination>
  </div>
</template>

<script>
export default {
  data() {
    return {
      pagination: {
        records: [],
        //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 6 //每页条数
      },
      webSite: 'http://8.130.16.20:8080/' // 站点地址
    };
  },
  // Vue对象内置函数，在此函数处hook，可在页面绘制时执行特定函数
  created() {
    // 分页获取未完成的考试信息
    this.getExamInfo();
  },
  methods: {
    // 进入考试
    entryExam(testId, paperId) {
      this.$router.push({path: '/stuTodoExam', query: {
        tid: testId,
        sid: 5,
        pid: paperId}});  // [WARNING] 注意从cookie中获取
    },
    // 分页获取未完成的考试信息
    getExamInfo() {
      let submitData = {
        sid: 5, // [WARNING] 注意从cookie中获取
        pageNum: this.pagination.current,
        pageSize: this.pagination.size
      }
      this.$axios({
        url: this.webSite + 'Stu/ShowTest',
        method: 'post',
        data: submitData
      }).then(res => {
        this.pagination.records = res.data.data.tests;
      })
      .catch(error => {});
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getExamInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getExamInfo();
    },
    // Vue表格组件回调函数，使表格各行显示不同叠层样式
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex % 2 == 0) {
        return "warning-row";
      } else {
        return "success-row";
      }
    }
  }
};
</script>
<style lang="scss" scoped>
.exam {
  padding: 0px 40px;
  .page {
    margin-top: 20px;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  .edit {
    margin-left: 20px;
  }
  .el-table tr {
    background-color: #DD5862 !important;
  }
}
.el-table .warning-row {
  background: #000 !important;

}

.el-table .success-row {
  background: #DD5862;
}

</style>
