import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.fly_app.screens.other.OtherViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun OtherScreen(viewModel: OtherViewModel = viewModel()) {
    Column {
        Text("other")
    }
}

@Preview
@Composable
fun OtherScreenPreview() {
    OtherScreen()
}
