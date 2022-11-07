import {createRouter, createWebHashHistory, RouteRecordRaw} from "vue-router";
import { usePermissStore } from '../store/permiss'
import Home from "../views/home.vue";

const routes:RouteRecordRaw[] = [
    {
        path: '/',
        redirect: '/dashboard'
    }, {
        path: "/",
        name: "Home",
        component: Home,
        children: [
            {
                path: "/dashboard",
                name: "dashboard",
                meta: {
                    title: '系统首页',
                    permiss: '1'
                },
                component: () => import ( /* webpackChunkName: "dashboard" */ "../views/dashboard.vue")
            }, 
            {
                path: "/apply",
                name: "apply",
                meta: {
                    title: '融资意向',
                    permiss: '2'
                },
                component: () => import ( /* webpackChunkName: "table" */ "../views/apply.vue")
            }, 
            {
                path: "/manage",
                name: "manage",
                meta: {
                    title: '排券',
                    permiss: '3'
                },
                component: () => import ( /* webpackChunkName: "charts" */ "../views/manage.vue")
            },  
            {
                path: "/opponent",
                name: "opponent",
                meta: {
                    title: '交易对手',
                    permiss: '4'
                },
                component: () => import ( /* webpackChunkName: "charts" */ "../views/opponent.vue")
            },  
            {
                path: '/user',
                name: 'user',
                meta: {
                    title: '个人中心'
                },
                component: () => import (/* webpackChunkName: "user" */ '../views/user.vue')
            }
        ]
    }, {
        path: "/login",
        name: "Login",
        meta: {
            title: '登录'
        },
        component: () => import ( /* webpackChunkName: "login" */ "../views/login.vue")
    }, {
        path: '/403',
        name: '403',
        meta: {
            title: '没有权限'
        },
        component: () => import (/* webpackChunkName: "403" */ '../views/403.vue')
    },
];

const router = createRouter({
    history: createWebHashHistory(),
    routes
});

router.beforeEach((to, from, next) => {
    document.title = `${to.meta.title} | 统一排券系统`;
    const role = localStorage.getItem('ms_username');
    const permiss = usePermissStore();
    if (!role && to.path !== '/login') {
        next('/login');
    } else if (to.meta.permiss && !permiss.key.includes(to.meta.permiss)) {
        // 如果没有权限，则进入403
        next('/403');
    } else {
        next();
    }
});

export default router;