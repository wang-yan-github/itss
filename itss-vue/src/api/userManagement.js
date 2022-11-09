import request from '@/utils/request'

//用户分页
export function getList(data) {
  return request({
    url: '/sysuser/getPage.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}
//根据事件服务群组搜索用户
export function getGroupPage(data) {
  return request({
    url: '/sysuser/getGroupPage.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//根据ID查询用户
export function getOneUser(data) {
  return request({
    url: '/sysuser/getUser.do',
    method: 'post',
    params: data,
  })
}

//新增用户
export function addUser(data) {
  return request({
    url: '/sysuser/save.do',
    method: 'post',
    data,
  })
}

//用户修改
export function updateUser(data) {
  return request({
    url: '/sysuser/edit.do',
    method: 'post',
    data,
  })
}

//用户设置角色
export function addUserRole(data) {
  return request({
    url: '/sysuser/addUserRole.do',
    method: 'post',
    data,
  })
}

//用户导出Excel
export function exporUserExcel() {
  return request({
    url: '/sysuser/exporUserExcel.do',
    method: 'post',
    responseType: 'blob'
  })
}

//用户角色导出Excel
export function exporUserRoleExcel() {
  return request({
    url: '/sysuser/exporUserRoleExcel.do',
    method: 'post',
    responseType: 'blob'
  })
}


//用户导入下载模板
export function downloadUser() {
  return request({
    url: '/sysuser/downloadUser',
    method: 'post',
    // headers:{
    //  'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    // },
    responseType: 'blob'
  })
}


//获得角色对应的权限
export function getSysAuthority(data) {
  return request({
    url: '/sysuser/getSysAuthority.do',
    method: 'post',
    params: data,
  })
}


//查询所有角色
export function selectAllRole() {
  return request({
    url: '/sysrole/selectAllRole.do',
    method: 'post',
  })
}


//分页查询所有角色
export function selectAllRoleByPage(data) {
  return request({
    url: '/sysrole/getPage.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//删除用户
export function doDelete(data) {
  return request({
    url: '/sysuser/deleteUser.do',
    method: 'post',
    params: data,
  })
}

//角色分页
export function getPage(data) {
  return request({
    url: '/sysrole/getPage.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

//角色新增
export function doEdit(data) {
  return request({
    url: '/sysrole/save.do',
    method: 'post',
    data,
  })
}


//角色修改
export function updateRole(data) {
  return request({
    url: '/sysrole/update.do',
    method: 'post',
    data,
  })
}


//删除角色
export function deleteSysRole(data) {
  return request({
    url: '/sysrole/deleteSysRole.do',
    method: 'post',
    params: data,
  })
}

//删除角色对应的用户
export function deleteRoleAndUser(data) {
  return request({
    url: '/sysrole/deleteRoleAndUser.do',
    method: 'post',
    data,
  })
}

//增加角色对应的用户
export function addRoleAndUser(data) {
  return request({
    url: '/sysrole/addRoleAndUser.do',
    method: 'post',
    data,
  })
}

//查看所有权限
export function getSysAuthorityByTree(data) {
  return request({
    url: '/sysauthority/getSysAuthorityByTree.do',
    method: 'post',
    data,
  })
}

export function getAllPermission(data) {
  return request({
    url: '/sysuser/getAllPermission.do/' + data,
    method: 'get',
  })
}

//设置权限
export function powerSysRole(data) {
  return request({
    url: '/sysrole/powerSysRole.do',
    method: 'post',
    data,
  })
}

//查看角色对应的权限
export function getSysAuthorityWithRole(data) {
  return request({
    url: '/sysrole/getSysAuthority.do',
    method: 'post',
    params: data
  })
}

//查询所有部门
export function getDepartmentWithTree(data) {
  return request({
    url: '/sysdepartment/getWithTree.do',
    method: 'post',
    data,
  })
}

//查询所有部门
export function getAllDept(data) {
  return request({
    url: '/sysdepartment/getAll.do',
    method: 'get',
    data,
  })
}

//查询所有服务群组
export function getAllInfo(data) {
  return request({
    url: '/eventServiceManage/toList.do',
    method: 'post',
    data,
  })
}

//查看角色对应的用户
export function getRoleAndUser(data) {
  return request({
    url: '/sysrole/getRoleAndUser.do',
    method: 'post',
    params: data
  })
}
//查看角色对应的用户
export function getUserByRoleId(data) {
  return request({
    url: '/sysrole/getUserByRoleId.do',
    method: 'post',
    params: data
  })
}

//查看用户对应的角色
export function getRoleIdByUser(data) {
  return request({
    url: '/sysrole/getRoleIdByUser.do',
    method: 'post',
    params: data
  })
}


//导出角色用户
export function exporRoleWithUserExcel() {
  return request({
    url: '/sysrole/exporRoleExcel.do',
    method: 'post',
    responseType: 'blob'
  })
}

//导出角色权限
export function exporRoleAuthorityExcel() {
  return request({
    url: '/sysrole/exporRoleAuthorityExcel.do',
    method: 'post',
    responseType: 'blob'
  })
}


//部门管理
export function sysDepartmentGetList(data) {
  return request({
    url: '/sysdepartment/getWithTree.do',
    method: 'post',
    data,
  })
}

export function sysDepartmentToAdd(data) {
  return request({
    url: '/sysdepartment/save.do',
    method: 'post',
    data,
  })
}

export function sysDepartmentToEdit(data) {
  return request({
    url: '/sysdepartment/update.do',
    method: 'post',
    data,
  })
}

export function getSysDepartmentInfo(data) {
  return request({
    url: '/sysdepartment/getDepartment.do',
    method: 'post',
    data,
  })
}

export function sysDepartmentToDel(data) {
  return request({
    url: '/sysdepartment/deleteDepartment.do',
    method: 'post',
    data,
  })
}

//公司管理
export function sysCompanyGetList(data) {
  return request({
    url: '/syscompany/getPage.do',
    method: 'post',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8'      //改这里就好了
    },
    data,
  })
}

export function sysCompanyToAdd(data) {
  return request({
    url: '/syscompany/save.do',
    method: 'post',
    data,
  })
}

export function sysCompanyToEdit(data) {
  return request({
    url: '/syscompany/update.do',
    method: 'post',
    data,
  })
}
// 根据公司id查询用户信息
export function getCompanyUser(data) {
  return request({
    url: '/syscompany/getCompanyUser',
    method: 'post',
    data,
  })
}

//========================================================系统设置=SQL报表=================================================
// 系统设置sql报表=分页
export function sqlPageListData(data) {
  return request({
    url: '/sysSql/toList.do',
    method: 'post',
    data,
  })
}

// 系统设置sql报表=查看
export function getsqlByIdData(data) {
  return request({
    url: '/sysSql/getById.do',
    method: 'post',
    data,
  })
}

// 系统设置sql报表=添加
export function tosqlAdd(data) {
  return request({
    url: '/sysSql/toAdd.do',
    method: 'post',
    data,
  })
}

// 系统设置sql报表=编辑
export function tosqlEdit(data) {
  return request({
    url: '/sysSql/toEdit.do',
    method: 'post',
    data,
  })
}

// 系统设置sql报表=删除
export function deleteSysSql(data) {
  return request({
    url: '/sysSql/deleteSysSql.do',
    method: 'post',
    data,
  })
}

// 系统设置sql报表=禁用启用
export function stateUpd(data) {
  return request({
    url: '/sysSql/stateUpd.do',
    method: 'post',
    data,
  })
}

// 系统设置sql报表=详情
export function details(data) {
  return request({
    url: '/sysSql/details.do',
    method: 'post',
    data,
  })
}

// 系统设置sql报表=导出
// export function sysSqlExport(data) {
//   return request({
//     url: '/sysSql/sysSqlExport.do',
//     method: 'post',
//     data,
//   })
// }
export function sysSqlExport(data) {
  return request({
    url: '/sysSql/sysSqlExport.do',
    method: 'post',
    responseType: 'blob',
    data
  })
}
