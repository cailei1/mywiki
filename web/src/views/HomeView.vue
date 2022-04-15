<template>
    <a-layout>
        <a-layout-sider width="200" style="background: #fff">
            <a-menu
                    v-model:selectedKeys="selectedKeys2"
                    v-model:openKeys="openKeys"
                    mode="inline"
                    :style="{ height: '100%', borderRight: 0 }"
            >
                <a-sub-menu key="sub1">
                    <template #title>
              <span>
                <user-outlined/>
                subnav 1
              </span>
                    </template>
                    <a-menu-item key="1">option1</a-menu-item>
                    <a-menu-item key="2">option2</a-menu-item>
                    <a-menu-item key="3">option3</a-menu-item>
                    <a-menu-item key="4">option4</a-menu-item>
                </a-sub-menu>
                <a-sub-menu key="sub2">
                    <template #title>
              <span>
                <laptop-outlined/>
                subnav 2
              </span>
                    </template>
                    <a-menu-item key="5">option5</a-menu-item>
                    <a-menu-item key="6">option6</a-menu-item>
                    <a-menu-item key="7">option7</a-menu-item>
                    <a-menu-item key="8">option8</a-menu-item>
                </a-sub-menu>
                <a-sub-menu key="sub3">
                    <template #title>
              <span>
                <notification-outlined/>
                subnav 3
              </span>
                    </template>
                    <a-menu-item key="9">option9</a-menu-item>
                    <a-menu-item key="10">option10</a-menu-item>
                    <a-menu-item key="11">option11</a-menu-item>
                    <a-menu-item key="12">option12</a-menu-item>
                </a-sub-menu>
            </a-menu>
        </a-layout-sider>
        <a-layout-content
                :style="{ background: '#fff', padding: '24px', margin: 0, minHeight: '500px' }"
        >

            <a-list item-layout="vertical" size="large" :grid="{gutter: 20, column: 3}"
                    :data-source="eBooks">
                <template #renderItem="{ item }">
                    <a-list-item key="item.title">
                        <template #actions>
          <span v-for="{ type, text } in actions" :key="type">
            <component :is="type" style="margin-right: 8px"/>
            {{ text }}
          </span>
                        </template>
                        <a-list-item-meta :description="item.description">
                            <template #title>
                                <a :href="item.href">{{ item.name }}</a>
                            </template>
                            <template #avatar>
                                <a-avatar :src="item.cover"/>
                            </template>
                        </a-list-item-meta>
                        {{ item.content }}
                    </a-list-item>
                </template>
            </a-list>

        </a-layout-content>
    </a-layout>
</template>

<!--<script setup lang="ts">-->
<!--    // TypeScript enabled-->
<!--   -->

<!--    const count = ref(1)-->
<!--</script>-->

<script>
    import {StarOutlined, LikeOutlined, MessageOutlined} from '@ant-design/icons-vue';
    import {defineComponent, onMounted, ref} from 'vue';
    import HelloWorld from '@/components/HelloWorld.vue';
    import axios from 'axios';

    const listData = [];

    for (let i = 0; i < 23; i++) {
        listData.push({
            href: 'https://www.antdv.com/',
            title: `ant design vue part ${i}`,
            avatar: 'https://joeschmoe.io/api/v1/random',
            description:
                'Ant Design, a design language for background applications, is refined by Ant UED Team.',
            content:
                'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.',
        });
    }


    export default defineComponent({
        name: 'HomeView',
        components: {
            HelloWorld,
            StarOutlined,
            LikeOutlined,
            MessageOutlined,
        },
        setup() {
            const eBooks = ref();

            const pagination = {
                onChange: (page) => {
                    console.log(page);
                },
                pageSize: 3,
            };

            const actions = [
                {type: 'StarOutlined', text: '156'},
                {type: 'LikeOutlined', text: '156'},
                {type: 'MessageOutlined', text: '2'},
            ];

            onMounted(() => {
                axios.get("/books").then(response => {
                    console.log("setUp")
                    const data = response.data;
                    eBooks.value = data.data;
                    console.log(response)
                });
            });

            return {
                eBooks,
                listData,
                pagination,
                actions,
            }
        }

    });
</script>

<style scoped>
    .ant-avatar {
        margin: 5px 0;
        padding: 0;
        color: rgba(0, 0, 0, 0.85);
        font-size: 14px;
        font-variant: tabular-nums;
        line-height: 50px;
        list-style: none;
        font-feature-settings: 'tnum';
        position: relative;
        display: inline-block;
        overflow: hidden;
        color: #fff;
        white-space: nowrap;
        text-align: center;
        vertical-align: middle;
        background: #ccc;
        width: 50px;
        height: 50px;
        border-radius: 8%;
    }
</style>
