import{b as y,H as h,d as _,u as m,o as r,a as d,n as o,f as a,h as t,v as c,t as l,g as f,s as n,K as v,_ as g,j as S,e as C,w as u}from"./index-BEALDuwY.js";import{_ as $}from"./_plugin-vue_export-helper-Cm_HEwFR.js";const b=y({header:{type:String,default:""},footer:{type:String,default:""},bodyStyle:{type:h([String,Object,Array]),default:""},bodyClass:String,shadow:{type:String,values:["always","hover","never"],default:"always"}}),w=_({name:"ElCard"}),k=_({...w,props:b,setup(i){const s=m("card");return(e,p)=>(r(),d("div",{class:o([a(s).b(),a(s).is(`${e.shadow}-shadow`)])},[e.$slots.header||e.header?(r(),d("div",{key:0,class:o(a(s).e("header"))},[t(e.$slots,"header",{},()=>[c(l(e.header),1)])],2)):f("v-if",!0),n("div",{class:o([a(s).e("body"),e.bodyClass]),style:v(e.bodyStyle)},[t(e.$slots,"default")],6),e.$slots.footer||e.footer?(r(),d("div",{key:1,class:o(a(s).e("footer"))},[t(e.$slots,"footer",{},()=>[c(l(e.footer),1)])],2)):f("v-if",!0)],2))}});var B=g(k,[["__file","card.vue"]]);const N=S(B),P={class:"header"},E={class:"extra"},V={__name:"PageContainer",props:{title:{required:!0,type:String}},setup(i){return(s,e)=>{const p=N;return r(),C(p,{class:"page-container"},{header:u(()=>[n("div",P,[n("span",null,l(i.title),1),n("div",E,[t(s.$slots,"extra",{},void 0,!0)])])]),default:u(()=>[t(s.$slots,"default",{},void 0,!0)]),_:3})}}},I=$(V,[["__scopeId","data-v-16992f7f"]]);export{I as _};
