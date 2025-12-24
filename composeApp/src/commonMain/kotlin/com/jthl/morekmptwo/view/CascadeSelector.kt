package com.jthl.morekmptwo.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jthl.morekmptwo.resources.Res
import com.jthl.morekmptwo.resources.icon_people_bg
import org.jetbrains.compose.resources.painterResource

/**
 * @author wanglei
 * @date 2025/12/18 14:30
 * @Description：
 */

@Composable
fun CascadeSelector(
    data: List<Group>, onSelectionChange: (List<Team>) -> Unit, parentRect: Rect
) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedGroup by remember { mutableStateOf<Group?>(null) }
    var selectedCompany by remember { mutableStateOf<Company?>(null) }
    var selectedTeamIds by remember { mutableStateOf(setOf<String>()) }
    var tempSelectedTeamIds by remember { mutableStateOf(setOf<String>()) }
    var searchQuery by remember { mutableStateOf("") }

    val filteredCompanies = remember(selectedGroup, searchQuery) {
        if (searchQuery.isEmpty() || selectedGroup == null) {
            selectedGroup?.companies ?: emptyList()
        } else {
            selectedGroup?.companies?.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            } ?: emptyList()
        }
    }

    val filteredTeams = remember(selectedCompany, searchQuery) {
        if (searchQuery.isEmpty() || selectedCompany == null) {
            selectedCompany?.teams ?: emptyList()
        } else {
            selectedCompany?.teams?.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            } ?: emptyList()
        }
    }


    fun updateSelection() {
        val selected = mutableListOf<Team>()
        data.forEach { group ->
            group.companies.forEach { company ->
                company.teams.forEach { team ->
                    if (selectedTeamIds.contains(team.id)) {
                        selected.add(team)
                    }
                }
            }
        }
        onSelectionChange(selected)
    }

    Box(
        modifier = Modifier.run {
            fillMaxWidth()
                .offset {
                    IntOffset(
                        x = parentRect.left.toInt(),
                        y = (parentRect.top + parentRect.height).toInt()
                    )
                }
        }
    ) {
        Column {
            Card(
                modifier = Modifier.fillMaxWidth().clickable {
                    isExpanded = !isExpanded
                },
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Column {
                        Text("车队组织", fontSize = 14.sp, color = Color.Gray)
                        Text(
                            text = "${data[0].name}",
                            fontSize = 16.sp,
                            color = Color.Black,
                            modifier = Modifier.padding(4.dp)
                        )
                    }
                    Icon(
                        painter = painterResource(Res.drawable.icon_people_bg),
                        contentDescription = null,
                        tint = Color(0xFFBFBFBF),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            AnimatedVisibility(
                visible = isExpanded, enter = expandVertically(), exit = shrinkVertically()
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column {

                        Row(
                            modifier = Modifier.fillMaxWidth().padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(1f).height(36.dp)
                                    .clip(RoundedCornerShape(18.dp)).background(Color(0xFFF5F5F5))
                                    .padding(horizontal = 12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                BasicTextField(
                                    value = searchQuery,
                                    onValueChange = { searchQuery = it },
                                    modifier = Modifier.weight(1f).padding(horizontal = 8.dp),
                                    textStyle = TextStyle(
                                        fontSize = 14.sp, color = Color.Black
                                    ),
                                    singleLine = true,
                                    cursorBrush = SolidColor(Color(0xFF409EFF)),
                                    decorationBox = { innerTextField ->
                                        if (searchQuery.isEmpty()) {
                                            Text(
                                                "搜索公司或车队",
                                                fontSize = 14.sp,
                                                color = Color.Gray
                                            )
                                        }
                                        innerTextField()
                                    })

                                if (searchQuery.isNotEmpty()) {
                                    Box(
                                        modifier = Modifier.size(20.dp)
                                            .clip(RoundedCornerShape(10.dp))
                                            .background(Color.Gray.copy(alpha = 0.2f))
                                            .clickable { searchQuery = "" },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            painter = painterResource(Res.drawable.icon_people_bg),
                                            contentDescription = null,
                                            tint = Color(0xFFBFBFBF),
                                            modifier = Modifier.size(20.dp)
                                        )

                                    }
                                }

                            }

                            Spacer(modifier = Modifier.width(8.dp))

                            // 全选按钮
                            TextButton(
                                onClick = {
                                    if (selectedCompany != null) {
                                        val allTeamIds =
                                            selectedCompany!!.teams.map { it.id }.toSet()
                                        val allSelected =
                                            allTeamIds.all { tempSelectedTeamIds.contains(it) }
                                        tempSelectedTeamIds = if (allSelected) {
                                            tempSelectedTeamIds - allTeamIds
                                        } else {
                                            tempSelectedTeamIds + allTeamIds
                                        }
                                    }
                                },
                                enabled = selectedCompany != null
                            ) {
                                Text(
                                    "全选",
                                    fontSize = 14.sp,
                                    color = if (selectedCompany != null) Color(0xFF409EFF) else Color.Gray
                                )
                            }

                            // 清空按钮
                            TextButton(
                                onClick = {
                                    tempSelectedTeamIds = emptySet()
                                }
                            ) {
                                Text(
                                    "清空",
                                    fontSize = 14.sp,
                                    color = Color(0xFFFF4D4F)
                                )
                            }
                        }

                        Divider(color = Color(0xFFE0E0E0))

                        //三级面板
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(350.dp)
                        ) {
                            //第一列表--集团级别
                            LazyColumn(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .background(Color(0xFFFAFAFA))
                            ) {
                                items(data) { group ->
                                    CascadeGroupItem(
                                        group = group,
                                        isSelected = selectedGroup == group,
                                        onClick = {
                                            selectedGroup = group
                                            selectedCompany = null
                                            searchQuery = ""
                                        })
                                }
                            }

                            Divider(
                                modifier = Modifier
                                    .width(1.dp)
                                    .fillMaxHeight(),
                                color = Color(0xFFE0E0E0)
                            )

                            //第二列
                            if (selectedGroup != null) {
                                LazyColumn(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .background(Color.White)
                                ) {
                                    if (filteredCompanies.isEmpty() && searchQuery.isNotEmpty()) {
                                        item {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(32.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    "未找到匹配的公司",
                                                    fontSize = 14.sp,
                                                    color = Color.Gray
                                                )
                                            }
                                        }
                                    } else {
                                        items(filteredCompanies) { company ->
                                            CascadeCompanyItem(
                                                company = company,
                                                isSelected = selectedCompany == company,
                                                onClick = {
                                                    selectedCompany = company
                                                }
                                            )
                                        }
                                    }
                                }
                            }

                            Divider(
                                modifier = Modifier
                                    .width(1.dp)
                                    .fillMaxHeight(),
                                color = Color(0xFFE0E0E0)
                            )

                            // 第三级：车队列表（支持多选）
                            if (selectedCompany != null) {
                                LazyColumn(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .background(Color.White)
                                ) {
                                    if (filteredTeams.isEmpty() && searchQuery.isNotEmpty()) {
                                        item {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(32.dp),
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Text(
                                                    "未找到匹配的车队",
                                                    fontSize = 14.sp,
                                                    color = Color.Gray
                                                )
                                            }
                                        }
                                    } else {
                                        items(filteredTeams) { team ->
                                            CheckboxTeamItem(
                                                team = team,
                                                isChecked = tempSelectedTeamIds.contains(team.id),
                                                onCheckedChange = { checked ->
                                                    tempSelectedTeamIds = if (checked) {
                                                        tempSelectedTeamIds + team.id
                                                    } else {
                                                        tempSelectedTeamIds - team.id
                                                    }
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        Divider(color = Color(0xFFE0E0E0))
                    }
                }
            }
            LaunchedEffect(isExpanded) {
                if (isExpanded) {
                    tempSelectedTeamIds = selectedTeamIds
                }
            }
        }
    }


}