using UnityEngine;

namespace Assambra.Client
{
    public class Helper : MonoBehaviour
    {
        public static void Quit()
        {
            #if UNITY_EDITOR
                UnityEditor.EditorApplication.isPlaying = false;
            #else
                Application.Quit();
            #endif
        }
    }
}

