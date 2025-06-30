package com.islam97.android.apps.users.presentation.ui.users

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.islam97.android.apps.users.R
import com.islam97.android.apps.users.domain.models.Gender
import com.islam97.android.apps.users.domain.models.User

@Composable
fun UserItem(modifier: Modifier = Modifier, user: User) {
    ConstraintLayout(
        modifier = modifier
    ) {
        val (profileImageReference, nameReference, ageReference, jobTitleReference, genderReference) = createRefs()
        val startGuideline = createGuidelineFromStart(4.dp)
        val topGuideline = createGuidelineFromTop(4.dp)
        val bottomGuideline = createGuidelineFromBottom(4.dp)
        Image(
            modifier = Modifier
                .constrainAs(profileImageReference) {
                    top.linkTo(topGuideline)
                    start.linkTo(startGuideline)
                    bottom.linkTo(bottomGuideline)
                    height = Dimension.fillToConstraints
                }
                .aspectRatio(1f)
                .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp)),
            painter = painterResource(
                id = when (user.gender) {
                    Gender.MALE -> R.drawable.ic_usr_male
                    Gender.FEMALE -> R.drawable.ic_user_female
                }
            ),
            contentDescription = null,
            contentScale = ContentScale.Fit)

        Text(
            modifier = Modifier
                .constrainAs(nameReference) {
                    top.linkTo(topGuideline)
                    start.linkTo(profileImageReference.end)
                }
                .padding(start = 8.dp), text = user.name)

        Text(
            modifier = Modifier
                .constrainAs(jobTitleReference) {
                    top.linkTo(nameReference.bottom)
                    start.linkTo(profileImageReference.end)
                }
                .padding(top = 4.dp, start = 8.dp), text = user.jogTitle)

        Text(
            modifier = Modifier
                .constrainAs(ageReference) {
                    top.linkTo(jobTitleReference.bottom)
                    start.linkTo(profileImageReference.end)
                }
                .padding(top = 4.dp, start = 8.dp), text = "${user.age} Year(s)")

        Text(
            modifier = Modifier
                .constrainAs(genderReference) {
                    top.linkTo(ageReference.bottom)
                    start.linkTo(profileImageReference.end)
                    bottom.linkTo(bottomGuideline)
                }
                .padding(top = 4.dp, start = 8.dp), text = user.gender.name)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewUserItem() {
    UserItem(
        user = User(
            name = "Islam Hani", age = 27, jogTitle = "Android Developer", gender = Gender.MALE
        )
    )
}