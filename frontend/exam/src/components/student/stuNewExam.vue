<!--
描述：学生端组件，查看未完成的考试列表
作者：211027134 叶怀生
时间：2022年2月5日14:47:56
-->
<template>
  <div class="exam">
    <el-table :data="pagination.records" border :row-class-name="tableRowClassName">
      <el-table-column fixed="left" prop="name" label="考试名称" width="240"></el-table-column>
      <el-table-column prop="start" label="考试开始时间" width="200"></el-table-column>
      <el-table-column prop="end" label="考试结束时间" width="200"></el-table-column>
      <el-table-column prop="description" label="考试描述" width="550"></el-table-column>
      <el-table-column prop="author" label="发布人" width="150"></el-table-column>
      <el-table-column fixed="right" label="操作" width="150">
        <template slot-scope="scope">
          <el-button @click="entryExam(scope.row.examCode)" type="primary" size="small">进入考试</el-button>
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
        records: [{
          name: '测试数据',
          start: '2022年2月5日15:03:40',
          end: '2022年2月5日15:03:48',
          description: '考试列表测试罢了',
          author: '老小子',
          examCode: 123
        }],
        //分页后的考试信息
        current: 1, //当前页
        total: null, //记录条数
        size: 6 //每页条数
      }
    };
  },
  created() {
    this.getPageInfo();
  },
  methods: {
    entryExam(examCode) { // 进入考试
      this.$router.push({path: '/stuTodoExam', query: {examCode: examCode}});
    },
    getPageInfo() {
      //分页查询所有试卷信息
      this.$axios(
        `/api/answers/${this.pagination.current}/${this.pagination.size}`
      )
        .then(res => {
          this.pagination = res.data.data;
          console.log(res);
        })
        .catch(error => {});
    },
    //改变当前记录条数
    handleSizeChange(val) {
      this.pagination.size = val;
      this.getPageInfo();
    },
    //改变当前页码，重新发送请求
    handleCurrentChange(val) {
      this.pagination.current = val;
      this.getPageInfo();
    },
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
