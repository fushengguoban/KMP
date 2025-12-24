package com.jthl.morekmptwo.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.jthl.morekmptwo.resources.Res
import com.jthl.morekmptwo.resources.icon_people_bg
import org.jetbrains.compose.resources.painterResource

/**
 * @author wanglei
 * @date 2025/12/17 16:03
 * @Description： 弹窗组件
 */

@Composable
fun CascadeSelectorDialog(
    data: List<Group>,
    onDismiss: () -> Unit,
    onConfirm: (List<Team>) -> Unit
) {
    var selectedGroup by remember { mutableStateOf<Group?>(null) }
    var selectedCompany by remember { mutableStateOf<Company?>(null) }
    var selectedTeamIds by remember { mutableStateOf(setOf<String>()) }
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.8f),
            shape = RoundedCornerShape(12.dp),
            color = Color.White
        ) {
            Column {
                //标题栏
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFF409EFF),
                    shadowElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "车队组织：${data[0].name}",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                        TextButton(onClick = {
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
                            onConfirm(selected)
                        }) {
                            Text("导出")
                        }
                    }
                }
                //三级联动
                Row(modifier = Modifier.fillMaxWidth().weight(1f)) {
                    LazyColumn(
                        modifier = Modifier.weight(1f).fillMaxHeight()
                            .border(0.5.dp, color = Color(0xFFE0E0E0))
                    ) {
                        items(data) { group ->
                            CascadeGroupItem(
                                group = group,
                                isSelected = selectedGroup == group,
                                onClick = {
                                    selectedGroup = group
                                    selectedCompany = null
                                }
                            )

                        }
                    }

                    if (selectedGroup != null) {
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .border(0.5.dp, Color(0xFFE0E0E0))
                        ) {
                            items(selectedGroup!!.companies) { company ->
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
                    // 第三级：车队列表（支持多选）
                    if (selectedCompany != null) {
                        LazyColumn(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .border(0.5.dp, Color(0xFFE0E0E0))
                        ) {
                            items(selectedCompany!!.teams) { team ->
                                CheckboxTeamItem(
                                    team = team,
                                    isChecked = selectedTeamIds.contains(team.id),
                                    onCheckedChange = { checked ->
                                        selectedTeamIds = if (checked) {
                                            selectedTeamIds + team.id
                                        } else {
                                            selectedTeamIds - team.id
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CascadeGroupItem(
    group: Group,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(if (isSelected) Color(0xFFE6F4FF) else Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = { onClick() },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF409EFF),
                uncheckedColor = Color(0xFFD9D9D9)
            )
        )
        Text(
            text = group.name, modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp), fontSize = 14.sp,
            color = if (isSelected) Color(0xFF409EFF) else Color.Black
        )
        Icon(
            painter = painterResource(Res.drawable.icon_people_bg),
            contentDescription = null,
            tint = Color(0xFFBFBFBF),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun CascadeCompanyItem(
    company: Company,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(if (isSelected) Color(0xFFE6F4FF) else Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isSelected,
            onCheckedChange = { onClick() },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF409EFF),
                uncheckedColor = Color(0xFFD9D9D9)
            )
        )

        Text(
            text = company.name,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            fontSize = 14.sp,
            color = if (isSelected) Color(0xFF409EFF) else Color.Black
        )

        Icon(
            painter = painterResource(Res.drawable.icon_people_bg),
            contentDescription = null,
            tint = Color(0xFFBFBFBF),
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun CheckboxTeamItem(
    team: Team,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onCheckedChange(!isChecked) }
            .background(if (isChecked) Color(0xFFF5F5F5) else Color.White)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF409EFF),
                uncheckedColor = Color(0xFFD9D9D9)
            )
        )

        Text(
            text = team.name,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 8.dp),
            color = Color.Black
        )
    }
}

fun createMockData(): List<Group> {
    return listOf(
        Group(
            id = "group1",
            name = "杭州公交集团",
            companies = listOf(
                Company(
                    id = "company1",
                    name = "一公司",
                    teams = listOf(
                        Team("team101", "101车队"),
                        Team("team102", "102车队"),
                        Team("team103", "103车队")
                    )
                ),
                Company(
                    id = "company2",
                    name = "二公司",
                    teams = listOf(
                        Team("team201", "201车队"),
                        Team("team202", "202车队"),
                        Team("team203", "203车队")
                    )
                ),
                Company(
                    id = "company3",
                    name = "三公司",
                    teams = listOf(
                        Team("team301", "301车队"),
                        Team("team302", "302车队")
                    )
                ),
                Company(
                    id = "company4",
                    name = "电车公司",
                    teams = listOf(
                        Team("team401", "401车队"),
                        Team("team402", "402车队"),
                        Team("team403", "403车队"),
                        Team("team404", "404车队")
                    )
                ),
                Company(
                    id = "company5",
                    name = "五公司",
                    teams = listOf(
                        Team("team501", "501车队"),
                        Team("team502", "502车队"),
                        Team("team503", "503车队")
                    )
                ),
                Company(
                    id = "company6",
                    name = "六公司",
                    teams = listOf(
                        Team("team601", "601车队"),
                        Team("team602", "602车队"),
                        Team("team603", "603车队"),
                        Team("team604", "604车队"),
                        Team("team605", "605车队")
                    )
                )
            )
        )
    )
}

data class Team(
    val id: String,
    val name: String
)

data class Company(
    val id: String,
    val name: String,
    val teams: List<Team>
)

data class Group(
    val id: String,
    val name: String,
    val companies: List<Company>
)

