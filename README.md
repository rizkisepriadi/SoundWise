# 🎧 Expert System for Sound System Diagnosis

![License](https://img.shields.io/badge/License-MIT-blue.svg)
![Platform](https://img.shields.io/badge/Platform-Android-green.svg)
![Kotlin](https://img.shields.io/badge/Kotlin-1.9.0-orange.svg)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Latest-purple.svg)

A mobile-based expert system built using Kotlin and Jetpack Compose to diagnose sound system issues and determine if the system is suitable for music events. This application leverages **Backward Chaining** for inference reasoning and **Certainty Factor** to handle uncertainty in user inputs.

## 📱 Features

- 🧠 Powerful inference engine implementing **Backward Chaining** method
- ✅ Uncertainty handling via **Certainty Factor (CF)** from user confidence inputs
- 🧾 Knowledge base represented in modular, maintainable JSON format
- 🧩 Clean MVVM architecture with **Koin** for dependency injection
- 🎨 Modern, responsive UI built with **Jetpack Compose**
- 📊 Detailed recommendations with confidence levels
- 📈 Final evaluation: "Layak digunakan" or "Tidak layak digunakan"

## 💡 Use Case

This expert system simulates how a professional sound technician evaluates a sound system by analyzing:

- Room acoustics and environment conditions
- Audio clarity and noise levels
- Equipment health status (microphone, amplifier, speakers)
- Electrical stability and power management
- Sound feedback issues and gain optimization
- Maintenance routines and practices

Based on user responses and their confidence values, the system calculates a comprehensive recommendation.

## 🧪 Example Facts Input

```kotlin
// Example input facts with confidence values
Fact("Ruangan", "Tidak Bergema") = 1.0
Fact("Suara", "Jernih") = 1.0
Fact("Microphone", "Baik") = 1.0
Fact("Speaker", "Baik") = 1.0
Fact("Arus Listrik", "Stabil") = 1.0
Fact("Amplifier", "Baik") = 1.0
Fact("Gain", "Optimal") = 1.0
Fact("Feedback", "Tidak Terjadi") = 1.0
Fact("Equalizer", "Baik") = 1.0
Fact("Pengecekan Kabel", "Teratur") = 1.0
Fact("Overheat", "Tidak") = 1.0
Fact("Perawatan Sound System", "Terawat") = 1.0

// Result based on these inputs:
Rekomendasi = "Layak digunakan" (CF: 0.95)
```

## 🧠 Reasoning Methods

### Backward Chaining
The app starts from the goal (recommendation) and works backward to determine if supporting facts and rules are satisfied, mimicking how an expert would approach sound system diagnostics.

### Certainty Factor
Each user response includes a confidence level (0.0 - 1.0), which combines with rule confidence factors to calculate the certainty of conclusions, addressing real-world uncertainty.

## 🔧 Tech Stack

- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM (Model-View-ViewModel)
- **Dependency Injection**: Koin
- **State Management**: StateFlow / LiveData
- **Knowledge Base**: JSON structured rulesets

## 🚀 Getting Started

1. Clone the repository
   ```bash
   git clone https://github.com/yourusername/sound-system-expert.git
   ```

2. Open in Android Studio

3. Build and run on an emulator or physical device

4. Answer the diagnosis questions with your confidence levels

5. Review the detailed system recommendation!

## 🔍 Project Structure

```
app/
├── src/main/
│   ├── java/com/app/sound_wise/
│   │   ├── data/                  # Data layer with repositories
│   │   ├── di/                    # Dependency injection modules
│   │   ├── ui/                    # UI components
│   │   │   ├── base/              # Reusable Compose UI components
│   │   │   ├── features/          # App screens
│   │   │   ├── navigation/          # App Navigation
│   │   │   └── theme/             # App theming
│   │   ├── utils/                 # Utility functions
│   │       ├── InferenceEngine/   # Inference engine implementation
│   │       ├── certaintyLabelToValue/   # to convert from label to value
│   └── assets/
│       └── rules.json # Expert system rule definitions
```

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 👥 Contribution

Contributions are welcome! Feel free to submit a Pull Request.

## 🔗 Connect

[![GitHub](https://img.shields.io/badge/GitHub-Profile-blue?style=flat&logo=github)](https://github.com/rizkisepriadi)
[![LinkedIn](https://img.shields.io/badge/LinkedIn-Profile-blue?style=flat&logo=linkedin)](https://linkedin.com/in/rizkisepriadi)
