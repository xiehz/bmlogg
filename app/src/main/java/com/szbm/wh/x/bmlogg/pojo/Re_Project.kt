package com.szbm.wh.x.bmlogg.pojo

import com.szbm.wh.x.bmlogg.vo.*

data class Re_Project(
        val ProjectPhase: List<ProjectPhase>,
        val ProjectArea:List<ProjectArea>,
        val sec_lineinfo: List<sec_lineinfo>,
        val C_Project_Lithology : List<C_Project_Lithology>,
        val C_Project_Stratum : List<C_Project_Stratum>,
        val c_project_stratum_ext : List<c_project_stratum_ext>
)