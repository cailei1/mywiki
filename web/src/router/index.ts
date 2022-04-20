import {createRouter, createWebHistory, RouteRecordRaw} from 'vue-router'
import HomeView from '../views/HomeView.vue';
import AboutView from '../views/AboutView.vue';
import EbookManagerPage from '../views/admin/EbookManagerPage.vue';
import CategoryManagerPage from "@/views/admin/CategoryManagerPage.vue";

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        name: 'home',
        component: HomeView
    },
    {
        path: '/about',
        name: 'about',
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: AboutView
    },
    {
        path: '/ebook_manager',
        name: 'ebookManager',
        component: EbookManagerPage
    },
    {
        path: '/category_manager',
        name: 'categoryManager',
        component: CategoryManagerPage
    }
]

const router = createRouter({
    history: createWebHistory(process.env.BASE_URL),
    routes
})

export default router
