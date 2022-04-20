<template>
  <a-layout>
    <a-layout-content
        :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '500px' }"
    >
      <p>
        <a-form layout="inline" :model="param">
          <a-form-item>
            <a-input v-model:value="param.name" placeholder="名称">
            </a-input>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="handleQuery({page: 1, size: pagination.pageSize})">
              查询
            </a-button>
          </a-form-item>
          <a-form-item>
            <a-button type="primary" @click="add()">
              新增
            </a-button>
          </a-form-item>
        </a-form>
      </p>
      <a-table
          :columns="columns"
          :data-source="categorys"
          :pagination="pagination"
          :loading="loading"
          @change="handleTableChange"
      >
        <template #cover="{ text: cover }">
          <img width="50" v-if="cover" :src="cover" alt="avatar"/>
        </template>
        <template v-slot:action="{ record }">
          <a-space size="small">

            <a-button type="primary" @click="edit(record)">
              编辑
            </a-button>

            <a-popconfirm
                title="删除后不可恢复，确认删除?"
                ok-text="是"
                cancel-text="否"
                @confirm="deletCategory(record.id)"
            >
              <a-button type="danger">
                删除
              </a-button>
            </a-popconfirm>
          </a-space>
        </template>
      </a-table>

    </a-layout-content>
  </a-layout>

  <a-modal
      title="电子书表单"
      v-model:visible="modalVisible"
      :confirm-loading="modalLoading"
      @ok="handleOk"
  >
    <a-form :model="category" :label-col="{ span: 6 }">
      <a-form-item label="名称">
        <a-input v-model:value="category.name"/>
      </a-form-item>
      <a-form-item label="父类">
        <a-input v-model:value="category.parent" type="textarea"/>
      </a-form-item>
      <a-form-item label="排序">
        <a-input v-model:value="category.sort" type="textarea"/>
      </a-form-item>
    </a-form>

  </a-modal>

</template>


<script lang="ts">
import {SmileOutlined, DownOutlined} from '@ant-design/icons-vue';
import {defineComponent, onMounted, ref} from 'vue';
import axios from 'axios';
import {message} from 'ant-design-vue'
import {Tool} from "@/utils/Tool";

export default defineComponent({
  name: "AdminEbook",
  components: {
    SmileOutlined,
    DownOutlined,
  },
  setup() {
    const categorys = ref();
    const pagination = ref({
      current: 1,
      pageSize: 4,
      total: 0
    });
    const loading = ref(false);
    const columns = [

      {
        title: '名称',
        dataIndex: 'name'
      },
      {
        title: '父类',
        key: 'parent',
      },
      {
        title: '排序',
        dataIndex: 'sort'
      },
      {
        title: 'Action',
        key: 'action',
        slots: {customRender: 'action'}
      }
    ];

    /**
     * 编辑框 属性
     */
    const modalVisible = ref(false)
    const modalLoading = ref(false)
    const handleOk = () => {
      modalLoading.value = true;
      axios.post("/category/save", category.value
      ).then((response) => {
        loading.value = false;
        modalLoading.value = false;
        modalVisible.value = false
        const data = response.data;

        console.log("data code:" + data.code)
        // eslint-disable-next-line no-empty
        if (data.code == 200) {
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize,
          });
          // categorys.value[editPos.value.positon] = category.value;
        } else {
          message.error(data.msg);
        }
      });
    }

    const deletCategory = (id: number) => {
      modalLoading.value = true;
      console.log("删除id为:" + id)
      axios.delete("/category/delete/" + id
      ).then((response) => {
        loading.value = false;
        modalLoading.value = false;
        modalVisible.value = false
        const data = response.data;
        // eslint-disable-next-line no-empty
        if (data.code == 200) {
          handleQuery({
            page: pagination.value.current,
            size: pagination.value.pageSize,
          });
          // categorys.value[editPos.value.positon] = category.value;
        }
      });
    }

    const add = () => {
      modalVisible.value = true;
      category.value = {};
    }

    /**
     * 数据查询
     **/
    const handleQuery = (params: any) => {
      loading.value = true;
      // 如果不清空现有数据，则编辑保存重新加载数据后，再点编辑，则列表显示的还是编辑前的数据
      categorys.value = [];
      axios.get("/category/bookLists", {
            params: {
              page: params.page,
              pageSize: params.pageSize,
              name: param.value.name
            }
          }
      ).then((response) => {
        loading.value = false;
        const data = response.data;

        if (data.code == 200) {
          categorys.value = data.data.pageLists;
          // 重置分页按钮
          pagination.value.current = params.page;
          pagination.value.total = data.data.total;
        } else {
          message.error(data.msg)
        }
      });
    };

    const handleTableChange = (pagination: any) => {
      handleQuery({
        page: pagination.current,
        pageSize: pagination.pageSize,
      })
    };


    const category = ref({})
    let editPos = ref({positon: 0})

    const edit = (record: any) => {
      modalVisible.value = true;
      category.value = Tool.copy(record);
      console.log("category.value = record" + record.id)
      // editPos.value.positon = categorys.value.indexOf(record)
    };


    onMounted(() => {
      handleQuery({
        page: 1,
        pageSize: pagination.value.pageSize
      })

    })

    // 查询新增按钮功能
    const param = ref();
    param.value = {};


    return {
      param,
      categorys,
      pagination,
      columns,
      loading,
      handleTableChange,
      modalVisible,
      modalLoading,
      handleOk,
      edit,
      category,
      editPos,
      add,
      deletCategory,
      handleQuery
    };
  },
});
</script>


